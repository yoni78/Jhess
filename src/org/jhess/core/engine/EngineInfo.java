package org.jhess.core.engine;

/**
 * Contains basic information about an engine.
 */
public class EngineInfo {

    private final String name;
    private final String author;

    public EngineInfo(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
