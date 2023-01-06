package app.filatov.deliverybooksundertreebot.service;

import app.filatov.deliverybooksundertreebot.bot.TelegramBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DownloadService {
    TelegramBot bot;

    public File findFileById(String fileId) throws TelegramApiException {
        GetFile getFileMethod = new GetFile();
        getFileMethod.setFileId(fileId);

        org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFileMethod);
        return bot.downloadFile(file.getFilePath());
    }
}
