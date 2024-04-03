#!/bin/bash

# Lancer le tunnel Smee pour le token Smee.io
screen -d -m -S smee smee --url https://smee.io/qtUEw6UwRKxYJA4 -t http://vmpx07.polytech.unice.fr:8000/multibranch-webhook-trigger/invoke?token=qtUEw6UwRKxYJA4

