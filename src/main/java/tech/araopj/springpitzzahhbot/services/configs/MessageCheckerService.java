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
package tech.araopj.springpitzzahhbot.services.configs;

import tech.araopj.springpitzzahhbot.configs.ModerationConfig;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import com.google.common.io.Resources;
import java.io.IOException;

@Service
public record MessageCheckerService(ModerationConfig moderationConfig) {

    public boolean searchForBadWord(String rawMessage) {
        return moderationConfig.warnings()
                .stream()
                .filter(rawMessage::contains)
                .anyMatch(rawMessage::equalsIgnoreCase);
    }

    /**
     * Loads the swear words from a list from a GitHub repository and adds the csv file to a
     * {@code List<String>}.
     * @throws IOException if the list is not present.
     */
    public void loadSwearWords() throws IOException {
        final var URL = URI.create("https://raw.githubusercontent.com/pitzzahh/list-of-bad-words/main/list.txt");
        moderationConfig.warnings().addAll(Resources.readLines(URL.toURL(), StandardCharsets.UTF_8));
    }

}
