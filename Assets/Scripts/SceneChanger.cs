using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
public class SceneChanger : MonoBehaviour
{
    // Start is called before the first frame update
    int theCurrentScene;
    public void ChangeToNextScene()
    {
        theCurrentScene = SceneManager.GetActiveScene().buildIndex;
        SceneManager.LoadScene(theCurrentScene + 1);
    }
    public void ChangeToStartScene()
    {
        SceneManager.LoadScene(0); 
    }
    public void QuitGame()
    {
        Application.Quit();

    }
}
