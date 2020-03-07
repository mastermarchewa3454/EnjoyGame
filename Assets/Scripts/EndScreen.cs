using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class EndScreen : MonoBehaviour
{
    TextMeshProUGUI levelText;
    TextMeshProUGUI timerText;

    public void Start()
    {
        levelText = GameObject.Find("StatsPanel/LevelContainer/LevelValue").GetComponent<TextMeshProUGUI>();
        string level = PlayerPrefs.GetInt("level", 0).ToString();
        levelText.text = level + " / 60";

        timerText = GameObject.Find("StatsPanel/TimeContainer/TimeValue").GetComponent<TextMeshProUGUI>();
        float timer = PlayerPrefs.GetFloat("timer", 0f);
        string minutes = Mathf.Floor(timer / 60).ToString("00");
        string seconds = (timer % 60).ToString("00");
        timerText.text = minutes + ":" + seconds;
    }

    public void ReturnMainMenu()
    {
        SceneManager.LoadScene(0);
    }
}
