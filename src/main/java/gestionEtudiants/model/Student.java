package gestionEtudiants.model;


public class Student {

    private Integer id;
    private String Matricule;
    private String firstname;
    private String lastname;
    private String age;
    private String gender;
    private String classe;

    //contructor use for table
    public Student(Integer id, String Matricule, String firstname, String lastname, String age, String gender, String classe) {
        this.id = id;
        this.Matricule = Matricule;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.classe = classe;
    }

   

//constructor to add
    public Student(String Matricule, String firstname, String lastname, String age, String gender, String classe) {
        this.Matricule = Matricule;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.classe = classe;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMatricule() {
        return Matricule;
    }

    public String getGender() {
        return gender;
    }

    public String getClasse() {
        return classe;
    }

    public String getAge() {
        return age;
    }

    public String getLastname() {
        return lastname;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setMatricule(String Matricule) {
        this.Matricule = Matricule;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
