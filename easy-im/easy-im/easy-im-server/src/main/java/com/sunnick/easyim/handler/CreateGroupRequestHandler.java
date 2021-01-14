package com.sunnick.easyim.handler;

import com.alibaba.fastjson.JSON;
import com.sunnick.easyim.packet.CreateGroupRequestPacket;
import com.sunnick.easyim.packet.CreateGroupResponsePacket;
import com.sunnick.easyim.util.GroupUtil;
import com.sunnick.easyim.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunnick on 2019/1/20/020.
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    private static Logger logger = LoggerFactory.getLogger(CreateGroupRequestHandler.class);

    public static CreateGroupRequestHandler getInstance(){
        return instance;
    }

    private CreateGroupRequestHandler(){}

    private static CreateGroupRequestHandler instance = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        CreateGroupResponsePacket response = createGroup(ctx,packet);
        logger.info("返回给客户端:{}",JSON.toJSONString(response));
        for (String userId : packet.getUsers()){
            SessionUtil.getChannelByUserId(userId).writeAndFlush(response);
        }
//        ctx.writeAndFlush(response);
    }

    private CreateGroupResponsePacket createGroup(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) {
        CreateGroupResponsePacket response = new CreateGroupResponsePacket();
        logger.info("收到创建群聊请求：{}", JSON.toJSONString(packet));

        List<String> users = packet.getUsers();
        String groupId = GroupUtil.createGroup(ctx,users);
        if(StringUtil.isNullOrEmpty(groupId)){
            response.setCode("1002");
            response.setMsg("创建群聊失败!");
        }
        List<String> userNames = getUserNames(users);

        response.setGroupId(groupId);
        response.setUserNames(userNames);

        return response;
    }

    private List<String> getUserNames(List<String> users) {
        List<String> userNames = new ArrayList<>();

        for(String id : users){
            Channel channel = SessionUtil.getChannelByUserId(id);
            if (channel != null){
                String name = SessionUtil.getSessionByChannel(channel).getUserName();
                userNames.add(name);
            }
        }
        return userNames;
    }
}
