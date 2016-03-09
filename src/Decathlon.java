import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;


@XmlRootElement
public class Decathlon {

    private Map<String, Competitor> allParticipants;

    public Decathlon() {

    }

      @XmlElementWrapper
    public Map<String, Competitor> getAllParticipants() {
        return allParticipants;
    }

    public void setAllParticipants(Map<String, Competitor> allParticipants) {
        this.allParticipants = allParticipants;
    }
}
