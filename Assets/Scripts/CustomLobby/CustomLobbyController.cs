using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class CustomLobbyController : MonoBehaviour
{
    DBCustomLobbyManager db;

    [SerializeField]
    TMP_InputField lobbyId;

    void Start()
    {
        db = GetComponent<DBCustomLobbyManager>();
    }

    public void JoinLobby()
    {
        StartCoroutine(db.JoinCustomLobby(lobbyId.text, questions => {
            if (questions == null)
                Debug.Log("Nope");
            else
            {
                FormatQuestions(questions);
                PlayerPrefs.SetInt("customLobby", 1);
                SceneManager.LoadScene("CharacterSelection");
            }
        }));
    }

    void FormatQuestions(string questions)
    {
        string[] split = questions.Split(new char[] { '{', '}', ',' }, System.StringSplitOptions.RemoveEmptyEntries);
        for (int i=0; i<split.Length / 2; i++)
        {
            string[] qns = split[2*i].Split('"');
            PlayerPrefs.SetString("qns" + i, qns[3]);
            string[] ans = split[2*i+1].Split('"');
            PlayerPrefs.SetString("ans" + i, ans[3]);
        }
    }
}
