package com.catAndDogStudio.cat.client;

import com.catAndDogStudio.catService.CatServiceRequest;
import io.netty.channel.Channel;

import java.io.IOException;

public class ClientCatService {

    public void sendMau(final Channel channel, final String catName, final String mau,
                        final CatServiceRequest.Mau.CatMood catMood) {
        CatServiceRequest.Request request = CatServiceRequest.Request.newBuilder()
                .setType(CatServiceRequest.RequestType.MAU)
                .setMau(CatServiceRequest.Mau.newBuilder()
                        .setCatId(catName)
                        .setMau(mau)
                        .setMood(catMood).build())
                .build();
        channel.writeAndFlush(request);
    }

    public void sendGiveFood(final Channel channel, final String foodName) throws IOException {
        CatServiceRequest.Request request = CatServiceRequest.Request.newBuilder()
                .setType(CatServiceRequest.RequestType.GIVE_FOOD)
                .setGiveFood(CatServiceRequest.GiveFood.newBuilder().setFoodName(foodName).build())
                .build();
        channel.writeAndFlush(request);
    }
}
