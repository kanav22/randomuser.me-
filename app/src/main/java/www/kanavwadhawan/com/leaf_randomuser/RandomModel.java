package www.kanavwadhawan.com.leaf_randomuser;

public class RandomModel
{
    private Results[] results;

    private Info info;

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    public Info getInfo ()
    {
        return info;
    }

    public void setInfo (Info info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+", info = "+info+"]";
    }
}