using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DBCustomLobbyManager : DBManager
{

    public IEnumerator SaveQuestionsAndAnswers(string[] questions, string[] answers)
    {
        string questionsJSON = "{\"qn1\":\"" + questions[0] + "\"," +
                            "\"qn2\":\"" + questions[1] + "\"," +
                            "\"qn3\":\"" + questions[2] + "\"," +
                            "\"qn4\":\"" + questions[3] + "\"," +
                            "\"qn5\":\"" + questions[4] + "\"," +
                            "\"qn6\":\"" + questions[5] + "\"," +
                            "\"qn7\":\"" + questions[6] + "\"," +
                            "\"qn8\":\"" + questions[7] + "\"," +
                            "\"qn9\":\"" + questions[8] + "\"," +
                            "\"qn10\":\"" + questions[9] + "\"," +
                            "\"qn11\":\"" + questions[10] + "\"," +
                            "\"qn12\":\"" + questions[11] + "\"," +
                            "\"qn13\":\"" + questions[12] + "\"," +
                            "\"qn14\":\"" + questions[13] + "\"," +
                            "\"qn15\":\"" + questions[14] + "\"," +
                            "\"qn16\":\"" + questions[15] + "\"," +
                            "\"qn17\":\"" + questions[16] + "\"," +
                            "\"qn18\":\"" + questions[17] + "\"," +
                            "\"qn19\":\"" + questions[18] + "\"," +
                            "\"qn20\":\"" + questions[19] + "\"}";


        string answersJSON =    "{\"ans1\":\"" + answers[0] + "\"," +
                            "\"ans2\":\"" + answers[1] + "\"," +
                            "\"ans3\":\"" + answers[2] + "\"," +
                            "\"ans4\":\"" + answers[3] + "\"," +
                            "\"ans5\":\"" + answers[4] + "\"," +
                            "\"ans6\":\"" + answers[5] + "\"," +
                            "\"ans7\":\"" + answers[6] + "\"," +
                            "\"ans8\":\"" + answers[7] + "\"," +
                            "\"ans9\":\"" + answers[8] + "\"," +
                            "\"ans10\":\"" + answers[9] + "\"," +
                            "\"ans11\":\"" + answers[10] + "\"," +
                            "\"ans12\":\"" + answers[11] + "\"," +
                            "\"ans13\":\"" + answers[12] + "\"," +
                            "\"ans14\":\"" + answers[13] + "\"," +
                            "\"ans15\":\"" + answers[14] + "\"," +
                            "\"ans16\":\"" + answers[15] + "\"," +
                            "\"ans17\":\"" + answers[16] + "\"," +
                            "\"ans18\":\"" + answers[17] + "\"," +
                            "\"ans19\":\"" + answers[18] + "\"," +
                            "\"ans20\":\"" + answers[19] + "\"}";


        yield return 15; //StartCoroutine(PostData("/users/" + userId + "/results", score)); As of now, ignore this cause you need the endpoints first 
        Debug.Log("Custom Lobby Created!");
    }

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

    public IEnumerator JoinCustomLobby(string lobbyId, System.Action<string> callback=null)
    {
        string lobbyString = "";
        yield return StartCoroutine(GetData("/custom-lobbies/"+lobbyId, callback:data => lobbyString = data));
        string[] split = lobbyString.Split(new char[] { '[', ']' }, System.StringSplitOptions.RemoveEmptyEntries);
        if (split.Length > 1)
            callback(split[1]);
        else
            callback(null);
    }

    [System.Serializable]
    public class CustomLobby
    {
        public string authorId;
        public string lobbyId;
    }
}