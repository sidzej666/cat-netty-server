package com.catAndDogStudio.cat.catProcessors;

import com.catAndDogStudio.cat.data.CatsData;
import com.catAndDogStudio.catService.CatServiceRequest;
import com.catAndDogStudio.catService.CatServiceResponse;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MauCatProcessor {
    private final CatAdministratorProcessor catAdministratorProcessor = new CatAdministratorProcessor();
    private final ChannelGroup allCats;
    private final CatsData catsData;

    public void mau(final Channel channel, final CatServiceRequest.Mau mau) {
        allCats.stream().forEach(c -> catAdministratorProcessor.notifyAboutNewCat(c, mau));
        channel.writeAndFlush(helloKitty(mau));
    }
    public void catHadLeft(final String channelId) {
        allCats.stream().forEach(c -> catAdministratorProcessor.notifyAboutCatThatLeft(c, catsData.getCatName(channelId)));
    }

    private CatServiceResponse.Response helloKitty(final CatServiceRequest.Mau mau) {
        return CatServiceResponse.Response.newBuilder()
                .setType(CatServiceResponse.ResponseType.MAU_RESPONSE)
                .setMauResponse(CatServiceResponse.MauResponse.newBuilder()
                        .setMau(String.format("HELLO KITTY, %s!", mau.getCatId()))
                        .setCatName(mau.getCatId())
                        .build())
                .build();
    }
}
