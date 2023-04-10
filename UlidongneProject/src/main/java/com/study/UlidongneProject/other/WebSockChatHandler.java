package com.study.UlidongneProject.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;
import com.study.UlidongneProject.service.Interface.ChatService;
import com.study.UlidongneProject.service.Interface.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    private final Map<Long, List<WebSocketSession>> sessionListByClub = new ConcurrentHashMap<>();
    private final ChatService chatService;

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
        }else {
            LocalDateTime currentTime = LocalDateTime.now();
            // message DB에 저장하기
            ChattingSaveRequestDto chattingDto = ChattingSaveRequestDto.builder()
                    .clubIdx(Long.parseLong(map.get("clubIdx")))
                    .memberIdx(Long.parseLong(map.get("memberIdx")))
                    .chattingType(map.get("chattingType"))
                    .chattingContent(map.get("chattingContent"))
                    .chattingWriteTime(currentTime)
                    .build();
            boolean success = chatService.save(chattingDto);
            if (success){
                List<WebSocketSession> sessions = sessionListByClub.get(Long.parseLong(map.get("clubIdx")));
                for (WebSocketSession s : sessions) {
                    try {
                        map.put("chattingWriteTime", currentTime.toString()); // chattingWriteTime
                        s.sendMessage(new TextMessage(mapper.writeValueAsString(map)));
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
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
}
