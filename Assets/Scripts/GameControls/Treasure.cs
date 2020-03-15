using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Treasure : MonoBehaviour
{
    void Start()
    {
        if (PlayerPrefs.GetInt("treasure", 1) == 0)
        {
            gameObject.SetActive(false);
            GameObject player = GameObject.Find("Player");
            player.transform.position = gameObject.transform.position;
        }
    }

    void OnCollisionEnter2D(Collision2D other)
    {
        Destroy(other.gameObject);

        if (other.gameObject.CompareTag("Player"))
        {
            PlayerPrefs.SetInt("treasure", 0);
            SceneManager.LoadScene("Item Choice");
        }
    }
}
