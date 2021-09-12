package io.rachidassouani.urlshortenerservice.service;

import com.google.common.hash.Hashing;
import io.rachidassouani.urlshortenerservice.dao.UrlRepository;
import io.rachidassouani.urlshortenerservice.dto.UrlDto;
import io.rachidassouani.urlshortenerservice.model.Url;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortUrl(UrlDto urlDto) {
        if (StringUtils.isNotEmpty(urlDto.getUrl())) {
            String hashedUrl = hashUrl(urlDto.getUrl());
            // saving url into the database
            Url urlToSave = new Url();
            urlToSave.setOriginalUrl(urlDto.getUrl());
            urlToSave.setShortUrl(hashedUrl);
            LocalDateTime time = LocalDateTime.now();
            urlToSave.setCreatedAt(time);
            urlToSave.setExpiredAt(time.plusMinutes(1));
            Url urlToReturn = urlRepository.save(urlToSave);

            return (urlToReturn != null) ? urlToReturn : null;
        }
        return null;
    }

    @Override
    public Url findByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        return (url != null) ? url : null;
    }

    private String hashUrl(String url) {
        LocalDateTime time = LocalDateTime.now();
        String hashedUrl = Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8)
                .toString();
        return hashedUrl;
    }
}
