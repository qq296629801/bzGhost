import $store from "@/store/index.js";
import { post } from "@/utils/request.js";

/**
 * 通用接口
 */

function messageCreate(message, msgType = 0) {
  let requestData = {
    chatId: $store.state.chatObj.chatId,
    chatType: $store.state.chatObj.chatType,
    userId: $store.state.userData.user.operId,
    message,
    msgType
  };
  post("app/msg/add", requestData);
}

export { messageCreate };
