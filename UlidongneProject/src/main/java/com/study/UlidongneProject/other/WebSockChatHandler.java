package com.study.UlidongneProject.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.UlidongneProject.dto.ChattingSaveRequestDto;
import com.study.UlidongneProject.service.Interface.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload(); // clubIdx,memberIdx,memberName,chattingType,chattingContent
        String[] payloadList = payload.split(",");



        // message DB에 저장하기
        ChattingSaveRequestDto chattingDto = ChattingSaveRequestDto.builder()
                    .clubIdx(Long.parseLong(payloadList[0]))
                    .memberIdx(Long.parseLong(payloadList[1]))
                    .chattingType(payloadList[3])
                    .chattingContent(payloadList[4])
                    .chattingWriteTime(LocalDateTime.now())
                    .build();
        boolean success = chatService.save(chattingDto);

        //


        System.out.println(payload);
        System.out.println(session);
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
//        room.handleActions(session, chatMessage, chatService);
    }
}
