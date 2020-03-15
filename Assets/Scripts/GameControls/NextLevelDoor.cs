using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class NextLevelDoor : MonoBehaviour
{
    SceneChanger sceneChanger;
    public void Start()
    {
        sceneChanger = FindObjectOfType<SceneChanger>();
    }
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("Player"))
        {
            GameObject[] allEnemies = GameObject.FindGameObjectsWithTag("Enemy");
            if (allEnemies.Length == 0 && PlayerPrefs.GetInt("treasure", 0) == 0)
                sceneChanger.ChangeToNextScene();
        }
    }
}
