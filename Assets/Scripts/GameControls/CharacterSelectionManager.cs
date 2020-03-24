using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class CharacterSelectionManager : MonoBehaviour
{
    public void ChooseArcher()
    {
        PlayerPrefs.SetInt("character", 1);
        SceneManager.LoadScene("StageSelection");
    }

    public void ChooseMole()
    {
        PlayerPrefs.SetInt("character", 2);
        SceneManager.LoadScene("StageSelection");
    }

    public void ChooseTreant()
    {
        PlayerPrefs.SetInt("character", 3);
        SceneManager.LoadScene("StageSelection");
    }

}
