package cs5.bozhchenko.agent;

/**
 * Created by motorcrue on 13.12.2017.
 */
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {
    private AID[] aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aids = aids;
    }

    @Override
    public void action() {

        if (aids != null) {
            ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
            message.setContent(firstName + "," + lastName);
            for (AID aid : aids) {
                message.addReceiver(aid);
            }
            myAgent.send(message);
        }

    }

    @Override
    public boolean done() {
        return true;
    }
}