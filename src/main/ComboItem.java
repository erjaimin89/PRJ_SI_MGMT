package main;

public class ComboItem {
	
	private long key;
    private String value;

    public ComboItem(long key, String value)
    {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public String toString()
    {
        return value;
    }

    public long getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

}
