package ua.softserve.ita.service.token.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.softserve.ita.service.token.VerificationTokenService;

@Service
@Transactional
public class TokensPurgeTask {

    private final VerificationTokenService verificationTokenService;

    public TokensPurgeTask(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @Scheduled(cron = "${purge.cron.cleanOldTokens}")
    public void purgeExpired() {
        verificationTokenService.deleteAllExpiredSince();
    }

}
