using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBCustomLobbyManager : DBManager
{
    public IEnumerator CreateCustomLobby(string[] questions, string[] answers, System.Action callback)
    {

        Debug.Log("START OF CREATE CUSTOM LOBBY");

        //Creation of Questonlist
        string questionList;
        questionList = "";
        for (int i = 0; i < 20; i++)
        {
            questionList += "{\"content\":\"" + questions[i] + "\"," +
                            "\"correctAnswer\":\"" + answers[i] + "\"}";
            if (i != 19)
            {
                questionList += ",";
            }
        }


        string customLobbyDetails = "{\"authorId\":\"" + userId + "\"," +
            "\"questions\":[" + questionList + "]}";


        string customLobbyString = "";

        yield return StartCoroutine(PostData("/custom-lobbies", customLobbyDetails, callback: data =>
        {
            gettingCustomLobby(data, callback);
        }));

        Debug.Log("Custom Lobby Created");
    }

    public void gettingCustomLobby(string customLobbyString, System.Action callback)
    {
        CustomLobby customlobby = JsonUtility.FromJson<CustomLobby>(customLobbyString);
        Debug.Log(customlobby.authorId);
        Debug.Log(customlobby.lobbyId);

        PlayerPrefs.SetString("SavedCLI", customlobby.lobbyId);
        callback();
    }
 



    public IEnumerator GetCustomLobbyID(System.Action callback)
    {
        yield return StartCoroutine(GetData("/custom-lobbies/"));
    }

    [System.Serializable]
    public class CustomLobby
    {
        public string authorId;
        public string lobbyId;
    }
}