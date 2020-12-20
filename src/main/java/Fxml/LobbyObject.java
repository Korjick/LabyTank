package Fxml;


import java.util.ArrayList;

public class LobbyObject {
    private ArrayList<Lobby> listOfLobbies = new ArrayList<>();

    public ArrayList<Lobby> getListOfLobby() {
        return listOfLobbies;
    }

    public void addLobby(Lobby lobby) {
        listOfLobbies.add(lobby);
    }

    public void removeLobby(Lobby lobby) {
        listOfLobbies.remove(lobby);
    }

    public int size() {
        return listOfLobbies.size();
    }

    public Lobby getLobby(int i) {
        return listOfLobbies.get(i);
    }

    public Lobby getLobby(String token) {
        for (int i = 0; i < listOfLobbies.size(); i++) {
            if (listOfLobbies.get(i).getName().equals(token)) {
                return listOfLobbies.get(i);
            }
        }
        return null;
    }
}
