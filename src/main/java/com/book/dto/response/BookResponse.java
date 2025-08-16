package com.book.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse implements Serializable {
    private Long id;

    private Long gutendexId;

    private String title;

    private List<AuthorResponse> authors;

    private List<String> summaries;

    private List<TranslatorResponse> translators;

    private List<String> subjects;

    private List<String> bookshelves;

    private List<String> languages;

    private Boolean copyright;

    @JsonProperty("media_type")
    private String mediaType;

    private Map<String, String> formats;

    @JsonProperty("download_count")
    private Integer downloadCount;

    private boolean alreadyExists;

    public boolean isAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(boolean alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

}
