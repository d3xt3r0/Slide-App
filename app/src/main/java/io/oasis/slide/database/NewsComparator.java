package io.oasis.slide.database;

import java.util.Comparator;

public class NewsComparator implements Comparator<News> {

    @Override
    public int compare(News news, News t1) {
        return news.getTime().compareTo(t1.getTime());
    }
}
