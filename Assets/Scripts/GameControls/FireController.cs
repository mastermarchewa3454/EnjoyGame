using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FireController : MonoBehaviour
{
    [SerializeField]
    private float bulletForce = 10f;

    [SerializeField]
    private float distance = 0.7f;

    public GameObject projectilePrefab;

    private Transform firePoint;
    private Rigidbody2D rb;
    private Camera cam;

    Vector2 aimPos;

    void Start()
    {
        firePoint = gameObject.transform.Find("FirePoint");
        rb = gameObject.GetComponent<Rigidbody2D>();
        cam = Camera.main;
    }

    // Update is called once per frame
    void Update()
    {
        if (gameObject.CompareTag("Player"))
        {
            aimPos = cam.ScreenToWorldPoint(Input.mousePosition);

            if (Input.GetButtonDown("Fire1"))
            {
                Shoot();
            }
        }
    }

    void FixedUpdate()
    {
        if (gameObject.CompareTag("Enemy"))
        {
            FindClosestPlayer();
        }

        Vector2 lookDir = aimPos - rb.position;
        float angle = Mathf.Atan2(lookDir.y, lookDir.x);
        firePoint.position = new Vector2(distance * Mathf.Cos(angle) + rb.position.x,
                                         distance * Mathf.Sin(angle) + rb.position.y);
        firePoint.transform.eulerAngles = new Vector3(firePoint.transform.eulerAngles.x,
                                                      firePoint.transform.eulerAngles.y,
                                                      angle * Mathf.Rad2Deg);
    }

    void FindClosestPlayer()
    {
        float distanceToClosest = Mathf.Infinity;
        GameObject closestPlayer = null;
        GameObject[] allPlayers = GameObject.FindGameObjectsWithTag("Player");

        if (allPlayers.Length != 0)
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

        aimPos = closestPlayer.transform.position;
    }

    public void Shoot()
    {
        GameObject projectile = Instantiate(projectilePrefab, firePoint.position, firePoint.rotation);
        Rigidbody2D projectileRb = projectile.GetComponent<Rigidbody2D>();
        projectileRb.AddForce(firePoint.right * bulletForce, ForceMode2D.Impulse);
    }
}

