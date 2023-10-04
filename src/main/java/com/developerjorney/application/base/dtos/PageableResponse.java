package com.developerjorney.application.base.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
public class PageableResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Pagination pagination;

    private final Collection<T> content;

    private PageableResponse(
        final Pagination pagination,
        final Collection<T> content
    ) {
        this.pagination = pagination;
        this.content = content;
    }

    public PageableResponse() {
        this.pagination = new Pagination();
        this.content = new HashSet<>();

    }

    public static <T> PageableResponse<T> create(
            final int currentPage,
            final int totalPage,
            final int size,
            final long count,
            final Collection<T> content
    ) {
      return new PageableResponse(
           new Pagination(
               currentPage,
               totalPage,
               size,
               count
           ),
          content
      );
    }

    public static <T, TInput> PageableResponse<T> create(
            final Page page,
            final Function<TInput, T> transformToViewModel
            ) {
        return new PageableResponse<>(
                new Pagination(
                        page.getNumber(),
                        page.getTotalPages(),
                        page.getSize(),
                        page.getTotalElements()
                ),
                (Collection<T>)page.getContent().stream()
                        .map(transformToViewModel)
                        .collect(Collectors.toSet())
        );
    }

    @Getter
    public static class Pagination implements Serializable {

        @JsonIgnore
        private final long serialVersionUID = 1L;

        private final int currentPage;

        private final int totalPage;

        private final int size;

        private final long totalCount;

        public Pagination(
            final int currentPage,
            final int totalPage,
            final int size,
            final long totalCount
        ) {
            this.currentPage = currentPage;
            this.totalPage = totalPage;
            this.size = size;
            this.totalCount = totalCount;
        }

        public Pagination() {
            this.currentPage = 0;
            this.totalPage = 0;
            this.size = 0;
            this.totalCount = 0;
        }
    }
}
