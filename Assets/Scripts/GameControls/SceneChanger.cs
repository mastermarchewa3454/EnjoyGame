using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
public class SceneChanger : MonoBehaviour
{

    GameObject player;
    Health playerHealth;

    int theCurrentScene;

    float timer;
    int level;

    void Start()
    {
        player = GameObject.Find("Player");
        playerHealth = player.GetComponent<Health>();
        level = PlayerPrefs.GetInt("level", 1);
    }

    public void ChangeToNextScene()
    {
        PlayerPrefs.SetInt("health", playerHealth.GetCurrHealth());
        PlayerPrefs.SetInt("level", level + 1);

        theCurrentScene = SceneManager.GetActiveScene().buildIndex;
        if (level % 3 == 0)
        {
            SceneManager.LoadScene(theCurrentScene - 2);
        }
        else
        {
            SceneManager.LoadScene(theCurrentScene + 1);
        }
    }
    public void ChangeToStartScene()
    {
        SceneManager.LoadScene(0);
    }

    public void ChangeToEndScene()
    {
        SceneManager.LoadScene("GameEnd");
    }

    public void QuitGame()
    {
        Application.Quit();

    }
}
