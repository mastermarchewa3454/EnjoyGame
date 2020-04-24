using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// DB manager for custom lobbies
/// </summary>
public class DBCustomLobbyManager : DBManager
{
    /// <summary>
    /// Creates a new custom lobby
    /// </summary>
    /// <param name="questions">Array of 20 questions</param>
    /// <param name="answers">Array of 20 answers</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
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

    /// <summary>
    /// Parses data from database.
    /// </summary>
    /// <param name="customLobbyString">JSON string from database</param>
    /// <param name="callback">Callback</param>
    public void gettingCustomLobby(string customLobbyString, System.Action callback)
    {
        CustomLobby customlobby = JsonUtility.FromJson<CustomLobby>(customLobbyString);
        Debug.Log(customlobby.authorId);
        Debug.Log(customlobby.lobbyId);

        PlayerPrefs.SetString("SavedCLI", customlobby.lobbyId);
        callback();
    }

    /// <summary>
    /// Fetches data for a custom lobby, based on lobbyID.
    /// </summary>
    /// <param name="lobbyId">7 character lobby ID</param>
    /// <param name="callback">Callback</param>
    /// <returns></returns>
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

    /// <summary>
    /// Custom Lobby class to parse JSON
    /// </summary>
    [System.Serializable]
    public class CustomLobby
    {
        public string authorId;
        public string lobbyId;
    }
}