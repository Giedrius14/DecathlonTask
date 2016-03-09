import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Competitor {

    private String place;
    private String name;
    private Integer totalScore;
    private String[] inputData;

    public Competitor() {

    }

    public String getPlace() {
        return place;
    }
    @XmlElement
    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalScore() {
        return totalScore;
    }
    @XmlElement
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String[] getInputData() {
        return inputData;
    }
    @XmlElement
    public void setInputData(String[] inputData) {
        this.inputData = inputData;
    }

}
