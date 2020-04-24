using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

/// <summary>
/// Character selection scene
/// </summary>
public class CharacterSelectionManager : MonoBehaviour
{
    /// <summary>
    /// Player chose archer
    /// </summary>
    public void ChooseArcher()
    {
        PlayerPrefs.SetInt("character", 1);
        NextScene();
    }

    /// <summary>
    /// Player chose mole
    /// </summary>
    public void ChooseMole()
    {
        PlayerPrefs.SetInt("character", 2);
        NextScene();
    }

    /// <summary>
    /// Player chose treant
    /// </summary>
    public void ChooseTreant()
    {
        PlayerPrefs.SetInt("character", 3);
        NextScene();
    }

    /// <summary>
    /// Change scene
    /// </summary>
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
