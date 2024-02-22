package client.utilities;

import commons.SingleEntry;
import commons.User;
import commons.UserSession;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GridUtils {

    /**
     * Constructor for GridUtils
     */
    public GridUtils() {}

    /**
     * Fills a Grid with users
     * @param sorted Sorted list of users
     * @param scoreGrid A scoreGrid
     */
    public void addToGrid(List<User> sorted, GridPane scoreGrid) {
        Label[][] label = new Label[3][12];
        for (int i = 0; i <= 11; i++) {
            if (i < sorted.size()) {
                label[0][i] = new Label(i + 1 + ".");
                scoreGrid.add(label[0][i], 0, i);
                label[1][i] = new Label(sorted.get(i).getName());
                scoreGrid.add(label[1][i], 1, i);
                label[2][i] = new Label(String.valueOf(sorted.get(i).getScore()));
                scoreGrid.add(label[2][i], 2, i);
            } else {
                label[0][i] = new Label("");
                scoreGrid.add(label[0][i], 0, i);
                label[1][i] = new Label("");
                scoreGrid.add(label[1][i], 1, i);
                label[2][i] = new Label("");
                scoreGrid.add(label[2][i], 2, i);
            }
        }
    }

    /**
     * Sorts all users in a usersession to be sorted later
     * @param userSessionCommunication An instance of UserSessionCommunication
     * @param userSession The userSession
     * @param scoreGrid The scoreGrid
     */
    public void fillLeaderboard(UserSessionCommunication userSessionCommunication, UserSession userSession, GridPane scoreGrid) {
        List<User> users = userSessionCommunication.getAllUsers(userSession.getId());
        List<User> sorted = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        int size = users.size();


        for (User user : users) {
            scores.add(user.getScore());
        }

        for (int i = 0; i < size; i++) {
            int max = scores.indexOf(Collections.max(scores));
            sorted.add(users.get(max));
            users.remove(max);
            scores.remove(max);
        }

        addToGrid(sorted, scoreGrid);
    }

    /**
     * add single player to grid
     * @param sorted
     * @param scoreGrid
     */
    public void addToSingleGrid(List<SingleEntry> sorted, GridPane scoreGrid) {
        Label[][] label = new Label[3][12];
        for (int i = 0; i <= 11; i++) {
            if (i < sorted.size()) {
                label[0][i] = new Label(i + 1 + ".");
                scoreGrid.add(label[0][i], 0, i);
                label[1][i] = new Label(sorted.get(i).getName());
                scoreGrid.add(label[1][i], 1, i);
                label[2][i] = new Label(String.valueOf(sorted.get(i).getScore()));
                scoreGrid.add(label[2][i], 2, i);
            } else {
                label[0][i] = new Label("");
                scoreGrid.add(label[0][i], 0, i);
                label[1][i] = new Label("");
                scoreGrid.add(label[1][i], 1, i);
                label[2][i] = new Label("");
                scoreGrid.add(label[2][i], 2, i);
            }
        }
    }
}
