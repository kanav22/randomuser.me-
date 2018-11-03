package www.kanavwadhawan.com.leaf_randomuser;

public class Results
{
    private Picture picture;

    private String phone;

    private String email;

    private Dob dob;

    private Name name;

    public Picture getPicture ()
    {
        return picture;
    }

    public void setPicture (Picture picture)
    {
        this.picture = picture;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public Dob getDob ()
    {
        return dob;
    }

    public void setDob (Dob dob)
    {
        this.dob = dob;
    }

    public Name getName ()
    {
        return name;
    }

    public void setName (Name name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [picture = "+picture+", phone = "+phone+", email = "+email+", dob = "+dob+", name = "+name+"]";
    }
}

