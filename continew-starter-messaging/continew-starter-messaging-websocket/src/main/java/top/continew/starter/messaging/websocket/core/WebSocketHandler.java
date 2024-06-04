/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.starter.messaging.websocket.core;

import cn.hutool.core.convert.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import top.continew.starter.messaging.websocket.autoconfigure.WebSocketProperties;
import top.continew.starter.messaging.websocket.dao.WebSocketSessionDao;

/**
 * WebSocket 处理器
 *
 * @author WeiRan
 * @author Charles7c
 * @since 2.1.0
 */
public class WebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private final WebSocketProperties webSocketProperties;
    private final WebSocketSessionDao webSocketSessionDao;

    public WebSocketHandler(WebSocketProperties webSocketProperties, WebSocketSessionDao webSocketSessionDao) {
        this.webSocketProperties = webSocketProperties;
        this.webSocketSessionDao = webSocketSessionDao;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("WebSocket receive message. sessionId: {}, message: {}.", session.getId(), message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String sessionKey = Convert.toStr(session.getAttributes().get(webSocketProperties.getCurrentUserKey()));
        webSocketSessionDao.add(sessionKey, session);
        log.info("WebSocket connect successfully. sessionKey: {}.", sessionKey);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionKey = Convert.toStr(session.getAttributes().get(webSocketProperties.getCurrentUserKey()));
        webSocketSessionDao.delete(sessionKey);
        log.info("WebSocket connect closed. sessionKey: {}.", sessionKey);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WebSocket transport error. sessionId: {}.", session.getId(), exception);
    }
}
