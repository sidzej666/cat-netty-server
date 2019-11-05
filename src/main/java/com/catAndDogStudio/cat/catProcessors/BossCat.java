package com.catAndDogStudio.cat.catProcessors;

import com.catAndDogStudio.cat.data.CatsData;
import com.catAndDogStudio.catService.CatServiceRequest;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BossCat {

    private final GiveFoodCatProcessor giveFoodCatProcessor;
    private final MauCatProcessor mauCatProcessor;
    private final CatsData catsData;

    public void handleRequest(final Channel channel, final CatServiceRequest.Request request) {
        switch (request.getType()) {
            case MAU:
                catsData.setCatName(channel.id().toString(), request.getMau().getCatId());
                mauCatProcessor.mau(channel, request.getMau());
                break;
            case GIVE_FOOD:
                giveFoodCatProcessor.giveFood(channel, request.getGiveFood());
                break;
            default:
                throw new IllegalStateException("message not supported: " + request);
        }
    }
    public void catHadLeft(final String channelId) {
        mauCatProcessor.catHadLeft(channelId);
    }
}
