using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEditor.SceneManagement;

public class NextLevelDoor : MonoBehaviour
{
    SceneChanger sceneChanger;
    public void Start()
    {
        sceneChanger = FindObjectOfType<SceneChanger>();
    }
    private void OnCollisionEnter2D(Collision2D collision)
    {     
        sceneChanger.ChangeToNextScene();
    }
}
