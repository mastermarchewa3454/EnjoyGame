using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameHUD : MonoBehaviour
{
    public Text healthText;
    public Text levelText;
    public Text timerText;

    int health;
    int level;
    float timer;

    void Start()
    {
        health = PlayerPrefs.GetInt("health", 100);
        level = PlayerPrefs.GetInt("level", 1);
        timer = PlayerPrefs.GetFloat("timer", 0f);

        healthText.text = "Health: " + health + " / 100";
        levelText.text = "Level " + level;
    }

    void OnDestroy()
    {
        PlayerPrefs.SetFloat("timer", timer);
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
