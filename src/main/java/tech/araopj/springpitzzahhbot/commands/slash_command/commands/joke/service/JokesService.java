/*
 * MIT License
 *
 * Copyright (c) 2022 pitzzahh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tech.araopj.springpitzzahhbot.commands.slash_command.commands.joke.service;

import tech.araopj.springpitzzahhbot.commands.slash_command.commands.joke.entity.Category;
import tech.araopj.springpitzzahhbot.commands.slash_command.commands.joke.entity.Language;
import tech.araopj.springpitzzahhbot.config.HttpConfig;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
import com.google.gson.Gson;
import java.util.List;
import java.net.URI;

@Slf4j
@Service
public record JokesService(HttpConfig httpConfig) {

    public Collection<Category> getCategories() {
        var httpResponseCompletableFuture = httpConfig.httpClient()
                .sendAsync(HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create("https://jokes.araopj.tech/v1/resource/categories"))
                                .build(),
                        HttpResponse.BodyHandlers.ofString()
                );
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = httpResponseCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while getting categories", e);
            throw new RuntimeException(e);
        }
        return new Gson().fromJson(stringHttpResponse.body(), new TypeToken<List<Category>>(){}.getType());
    }

    public Collection<Language> getLanguages() {
        var httpResponseCompletableFuture = httpConfig.httpClient()
                .sendAsync(HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://jokes.araopj.tech/v1/resource/languages"))
                        .build(), HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> stringHttpResponse;
        try {
            stringHttpResponse = httpResponseCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error while getting languages", e);
            throw new RuntimeException(e);
        }
        return new Gson().fromJson(stringHttpResponse.body(), new TypeToken<List<Language>>(){}.getType());
    }

}