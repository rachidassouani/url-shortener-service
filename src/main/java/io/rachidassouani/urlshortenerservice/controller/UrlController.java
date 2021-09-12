package io.rachidassouani.urlshortenerservice.controller;

import io.rachidassouani.urlshortenerservice.dto.UrlDto;
import io.rachidassouani.urlshortenerservice.dto.UrlErrorResponseDto;
import io.rachidassouani.urlshortenerservice.dto.UrlResponseDto;
import io.rachidassouani.urlshortenerservice.model.Url;
import io.rachidassouani.urlshortenerservice.service.UrlService;
import io.rachidassouani.urlshortenerservice.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping("generateShortUrl")
    public ResponseEntity<?> generateShortUrl(@RequestBody UrlDto urlDto) {
        // generate and save the url
        Url url = urlService.generateShortUrl(urlDto);
        if (url != null) {
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setShortUrl(url.getShortUrl());
            urlResponseDto.setOriginalUrl(url.getOriginalUrl());
            urlResponseDto.setExpiredDate(url.getExpiredAt().toString());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setError(Constant.errorProcessingRequestMessage);
        urlErrorResponseDto.setStatus(Constant.errorProcessingRequestStatus);
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);

    }

    @GetMapping("redirect/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(
            @PathVariable("shortUrl") String shortUrl,
            HttpServletResponse response) throws IOException {
        // if received short Url is empty
        if (StringUtils.isEmpty(shortUrl)) {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError(Constant.shortUrlNotValidErrorMessage);
            urlErrorResponseDto.setStatus(Constant.shortUrlNotValidStatusMessage);
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        // searching for url by short url
        Url url = urlService.findByShortUrl(shortUrl);

        // if the url is not exist in the database
        if (url == null) {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError(Constant.shortUrlNotExistOrExpiredErrorMessage);
            urlErrorResponseDto.setStatus(Constant.shortUrlNotExistOrExpiredStatusMessage);
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        // if the url is expired
        if (url.getExpiredAt().isBefore(LocalDateTime.now())) {
            UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
            urlErrorResponseDto.setError(Constant.urlExpiredErrorMessage);
            urlErrorResponseDto.setStatus(Constant.urlExpiredStatusMessage);
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        // redirect the user to the original url
        response.sendRedirect(url.getOriginalUrl());
        return null;
    }
}
