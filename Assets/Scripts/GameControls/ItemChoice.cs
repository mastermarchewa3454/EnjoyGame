using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ItemChoice : MonoBehaviour
{
    public void GetHealth()
    {
        SceneManager.LoadScene("Item room");
    }

    public void GetAttack()
    {
        SceneManager.LoadScene("Item room");
    }

    public void GetSpeed()
    {
        SceneManager.LoadScene("Item room");
    }
}
