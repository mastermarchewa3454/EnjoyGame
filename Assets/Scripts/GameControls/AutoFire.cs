using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class AutoFire : NetworkBehaviour
{
    public float bulletForce = 10f;
    public float fpRadius = 0.7f;

    public Transform firePoint;
    public GameObject projectilePrefab;
    public Rigidbody2D rb;

    Vector2 playerPos;

    void FixedUpdate()
    {
        FindClosestPlayer();

        Vector2 lookDir = playerPos - rb.position;
        float angle = Mathf.Atan2(lookDir.y, lookDir.x);
        firePoint.position = new Vector2(fpRadius * Mathf.Cos(angle) + rb.position.x,
                                         fpRadius * Mathf.Sin(angle) + rb.position.y);
        firePoint.transform.eulerAngles = new Vector3(firePoint.transform.eulerAngles.x,
                                                      firePoint.transform.eulerAngles.y,
                                                      angle * Mathf.Rad2Deg);
    }

    void FindClosestPlayer()
    {
        float distanceToClosest = Mathf.Infinity;
        GameObject closestPlayer = null;
        GameObject[] allPlayers = GameObject.FindGameObjectsWithTag("Player");

        if (allPlayers != null)
        {
            foreach (GameObject player in allPlayers)
            {
                float distance = (player.transform.position - this.transform.position).sqrMagnitude;
                if (distance < distanceToClosest)
                {
                    distanceToClosest = distance;
                    closestPlayer = player;
                }
            }
        }

        playerPos = closestPlayer.transform.position;
    }

    [Command]
    public void CmdShoot()
    {
        GameObject projectile = Instantiate(projectilePrefab, firePoint.position, firePoint.rotation);
        Rigidbody2D projectileRb = projectile.GetComponent<Rigidbody2D>();
        projectileRb.AddForce(firePoint.right * bulletForce, ForceMode2D.Impulse);
        NetworkServer.Spawn(projectile);
    }
}
