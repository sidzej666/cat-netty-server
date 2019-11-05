package com.catAndDogStudio.cat.catProcessors;

import com.catAndDogStudio.catService.CatServiceRequest;
import com.catAndDogStudio.catService.CatServiceResponse;
import io.netty.channel.Channel;

public class CatAdministratorProcessor {
    public void notifyAboutNewCat(final Channel channel, final CatServiceRequest.Mau mau) {
        channel.writeAndFlush(
                CatServiceResponse.Response.newBuilder()
                        .setType(CatServiceResponse.ResponseType.SERVER_MESSAGE)
                        .setCatServerMessage(CatServiceResponse.CatServerMessage.newBuilder()
                                .setMessage(catHadJoinedMessage(mau))
                                .build()));
    }
    public void notifyAboutCatThatLeft(final Channel channel, final String catName) {
        channel.writeAndFlush(
                CatServiceResponse.Response.newBuilder()
                        .setType(CatServiceResponse.ResponseType.SERVER_MESSAGE)
                        .setCatServerMessage(CatServiceResponse.CatServerMessage.newBuilder()
                                .setMessage(catHadLeftMessage(catName))
                                .build()));
    }

    private String catHadJoinedMessage(final CatServiceRequest.Mau mau) {
        return String.format("MAU! %s has joined Cat Server! Cat mood: %s", mau.getCatId(), mau.getMood());
    }
    private String catHadLeftMessage(final String catName) {
        return String.format("NOT MAU, %s has left Cat Server", catName);
    }
}
