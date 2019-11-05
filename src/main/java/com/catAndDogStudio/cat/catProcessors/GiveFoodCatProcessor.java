package com.catAndDogStudio.cat.catProcessors;

import com.catAndDogStudio.catService.CatServiceRequest;
import com.catAndDogStudio.catService.CatServiceResponse;
import io.netty.channel.Channel;

public class GiveFoodCatProcessor {

    private int food = 10;

    public synchronized void giveFood(final Channel channel, final CatServiceRequest.GiveFood giveFood) {
        if (food > 0) {
            giveFood(channel, giveFood.getFoodName());
        } else {
            refuseFood(channel);
        }
    }

    private void giveFood(final Channel channel, final String foodName) {
        food--;
        channel.writeAndFlush(
                CatServiceResponse.Response.newBuilder()
                        .setType(CatServiceResponse.ResponseType.GIVE_FOOD_RESPONSE)
                        .setGiveFoodResponse(CatServiceResponse.GiveFoodResponse.newBuilder()
                                .setFoodForCat(true)
                                .setFoodName(foodName)
                                .build()));
    }
    private void refuseFood(final Channel channel) {
        channel.writeAndFlush(
                CatServiceResponse.Response.newBuilder()
                        .setType(CatServiceResponse.ResponseType.GIVE_FOOD_RESPONSE)
                        .setGiveFoodResponse(CatServiceResponse.GiveFoodResponse.newBuilder()
                                .setFoodForCat(false)
                                .setFoodName("NO FOOD!!! AAAAAAA!!!")
                                .build()));
    }
}
