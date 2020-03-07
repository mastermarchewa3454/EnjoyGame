using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameHeader : MonoBehaviour
{
    public Text timerText;
    public Text levelText;

    float timer;
    float level;

    void Start()
    {
        timer = PlayerPrefs.GetFloat("timer", 0f);
        level = PlayerPrefs.GetInt("level", 1);     // Level set in SceneChanger

        levelText.text = "Level " + level;
    }

    // Update is called once per frame
    void Update()
    {
        timer += Time.deltaTime;
        string minutes = Mathf.Floor(timer / 60).ToString("00");
        string seconds = (timer % 60).ToString("00");
        timerText.text = minutes + ":" + seconds;
    }
}
