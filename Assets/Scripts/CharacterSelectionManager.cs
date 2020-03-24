using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class CharacterSelectionManager : MonoBehaviour
{
    public int characterChoice;
    public void ChooseArcher()
    {
        SceneManager.LoadScene("StageSelection");
        characterChoice = 1;

    }

    public void ChooseMole()
    {
        SceneManager.LoadScene("StageSelection");
        characterChoice = 2;

    }

    public void ChooseTreant()
    {
        SceneManager.LoadScene("StageSelection");
        characterChoice = 3;

    }

}
