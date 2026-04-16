package space.ajcool.westerospaths.core.data.config.shared;

public enum Book
{
    AGOT("agot", "A Game of Thrones"),
    ACOK("acok", "A Clash of Kings"),
    ASOS("asos", "A Storm of Swords"),
    AFFC("affc", "A Feast for Crows"),
    ADWD("adwd", "A Dance with Dragons");

    private final String id;
    private final String displayName;

    Book(String id, String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId()
    {
        return id;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public static Book fromId(String id)
    {
        if (id == null || id.isEmpty()) return null;
        for (Book book : values())
        {
            if (book.id.equalsIgnoreCase(id))
            {
                return book;
            }
        }
        return null;
    }
}
