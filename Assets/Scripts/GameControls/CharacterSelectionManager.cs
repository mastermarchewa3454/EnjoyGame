using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class CharacterSelectionManager : MonoBehaviour
{
    public void ChooseArcher()
    {
        PlayerPrefs.SetInt("character", 1);
        NextScene();
    }

    public void ChooseMole()
    {
        PlayerPrefs.SetInt("character", 2);
        NextScene();
    }

    public void ChooseTreant()
    {
        PlayerPrefs.SetInt("character", 3);
        NextScene();
    }

    void NextScene()
    {
        if (PlayerPrefs.GetInt("customLobby", 0) == 1)
        {
            SceneManager.LoadScene("Level 1");
        }
        else
        {
            SceneManager.LoadScene("StageSelection");
        }
    }
}
