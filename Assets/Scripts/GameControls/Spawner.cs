using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class Spawner : NetworkBehaviour
{
    [SerializeField]
    private GameObject enemyPrefab;

    [SerializeField]
    private float spawnInterval = 1.0f;

    [SerializeField]
    private float enemySpeed = 1.0f;

    public override void OnStartServer()
    {
        InvokeRepeating("SpawnEnemy", this.spawnInterval, this.spawnInterval);
    }

    void SpawnEnemy()
    {
        Vector2 spawnPosition = new Vector2(this.transform.position.x, this.transform.position.y);
        GameObject enemy = Instantiate(enemyPrefab, spawnPosition, Quaternion.identity) as GameObject;
        NetworkServer.Spawn(enemy);
        Destroy(enemy, 10);
    }
}
