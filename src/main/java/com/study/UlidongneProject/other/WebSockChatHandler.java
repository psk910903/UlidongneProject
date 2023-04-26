package com.study.UlidongneProject.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;
import com.study.UlidongneProject.dto.ClubResponseDto;
import com.study.UlidongneProject.dto.MemberResponseDto;
import com.study.UlidongneProject.service.ClubServiceImpl;
import com.study.UlidongneProject.service.Interface.ChatService;
import com.study.UlidongneProject.service.Interface.MemberService;
import com.study.UlidongneProject.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    private final Map<Long, List<WebSocketSession>> sessionListByClub = new ConcurrentHashMap<>();
    private final ChatService chatService;
    private final MemberServiceImpl memberService;
    private final ClubServiceImpl clubService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload(); // clubIdx,memberIdx,memberName,chattingType,chattingContent, memberPicture
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(payload, Map.class);

        if (map.get("chattingType").equals("open")){
            List sessions = sessionListByClub.get(Long.parseLong(map.get("clubIdx")));
            if (sessions == null){
                sessions = new ArrayList<>();
                sessionListByClub.put(Long.parseLong(map.get("clubIdx")), sessions);
                sessions.add(session);
            } else {
                sessions.add(session);
            }
        }else if (map.get("chattingType").equals("join")){

            MemberResponseDto member = memberService.findMemberByIdxWOClubRepo(Long.parseLong(map.get("memberIdx")));
            map.put("memberName", member.getMemberName());
            map.put("chattingContent", member.getMemberName() + map.get("chattingContent"));

            saveAndSendMessage(map);

        } else {
            saveAndSendMessage(map);
        }
        
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        for( Map.Entry<Long, List<WebSocketSession>> elem : sessionListByClub.entrySet() ){
            List sessions = elem.getValue();
            boolean deleted = sessions.remove(session);
            if (deleted && sessions.size() == 0){
                sessionListByClub.remove(elem.getKey());
            }
        }
    }

    public boolean sendQuitMessage(Long memberIdx){

        Map<String, String> map = new HashMap<>();
        List<ClubResponseDto> clubList = clubService.findMemberJoinedClub(memberIdx);
        String memberName = memberService.findMemberByIdxWOClubRepo(memberIdx).getMemberName();

        for (ClubResponseDto club: clubList){

            // map initialize : clubIdx, memberIdx, memberName, chattingType, chattingContent, memberPicture(X)
            map.put("clubIdx", "" + club.getClubIdx());
            map.put("memberIdx", "" + memberIdx);
            map.put("memberName", memberName);
            map.put("chattingType", "quit");
            map.put("chattingContent", memberName + "님이 나갔습니다.");
            map.put("memberPicture", "");

            boolean success= saveAndSendMessage(map);
            if (!success){
                return false;
            }
        }
        return true;
    }

    public boolean saveAndSendMessage(Map<String, String> map) {

        LocalDateTime currentTime = LocalDateTime.now();
        ObjectMapper mapper = new ObjectMapper();

        // DB에 저장하기
        ChattingSaveRequestDto chattingDto = ChattingSaveRequestDto.builder()
                .clubIdx(Long.parseLong(map.get("clubIdx")))
                .memberIdx(Long.parseLong(map.get("memberIdx")))
                .chattingType(map.get("chattingType"))
                .chattingContent(map.get("chattingContent"))
                .chattingWriteTime(currentTime)
                .build();
        boolean success = chatService.save(chattingDto);

        // message 보내기
        if (success) {
            List<WebSocketSession> sessions = sessionListByClub.get(Long.parseLong(map.get("clubIdx")));
            if (sessions != null) {
                for (WebSocketSession s : sessions) {
                    try {
                        map.put("chattingWriteTime", currentTime.toString());
                        s.sendMessage(new TextMessage(mapper.writeValueAsString(map)));
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }else{
            return false;
        }

        return true;
    }
}
