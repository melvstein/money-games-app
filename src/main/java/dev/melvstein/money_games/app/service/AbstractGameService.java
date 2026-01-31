package dev.melvstein.money_games.app.service;

import dev.melvstein.money_games.app.Helper.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractGameService implements GameService {

    @Override
    public String getGameLaunchUrl() {
        log.debug("{} - getGameLaunchUrl: Not implemented yet", Util.getClassAndMethod());

        return "";
    }
}
