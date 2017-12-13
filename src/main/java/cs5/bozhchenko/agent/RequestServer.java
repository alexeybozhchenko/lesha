package cs5.bozhchenko.agent;

/**
 * Created by motorcrue on 13.12.2017.
 */
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import cs5.bozhchenko.User;
import cs5.bozhchenko.db.DaoFactory;
import cs5.bozhchenko.db.DatabaseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class RequestServer extends CyclicBehaviour {


    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else {
                Collection<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private Collection<User> parseMessage(ACLMessage message) {
        Collection<User> users = new LinkedList<>();

        String content = message.getContent();
        if (content != null) {
            StringTokenizer tokenizer1 = new StringTokenizer(content, ";");
            while (tokenizer1.hasMoreTokens()) {
                String userInfo = tokenizer1.nextToken();
                StringTokenizer tokenizer2 = new StringTokenizer(userInfo, ",");
                String id = tokenizer2.nextToken();
                String firstName = tokenizer2.nextToken();
                String lastName = tokenizer2.nextToken();
                users.add(new User(new Long(id), firstName, lastName, null));
            }
        }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();

        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if (tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users;
            try {
                users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            } catch (DatabaseException e) {
                e.printStackTrace();
                users = new ArrayList<>(0);
            }

            StringBuilder buffer = new StringBuilder();
            for (User user : users) {
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }

        return reply;
    }
}