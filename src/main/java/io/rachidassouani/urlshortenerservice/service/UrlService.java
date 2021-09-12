package io.rachidassouani.urlshortenerservice.service;

import io.rachidassouani.urlshortenerservice.dto.UrlDto;
import io.rachidassouani.urlshortenerservice.model.Url;

public interface UrlService {
    Url generateShortUrl(UrlDto urlDto);
    Url findByShortUrl(String shortUrl);
}
