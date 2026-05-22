# Contributing to LocalChat

Thank you for your interest in contributing! This guide covers everything you need to get started.

---

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How to Contribute](#how-to-contribute)
- [Development Setup](#development-setup)
- [Pull Request Process](#pull-request-process)
- [Commit Convention](#commit-convention)
- [Code Style](#code-style)
- [Reporting Bugs](#reporting-bugs)
- [Suggesting Features](#suggesting-features)

---

## Code of Conduct

Be respectful. Be constructive. We welcome contributors of all skill levels.

---

## How to Contribute

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make your changes
4. Test locally (both backend and frontend)
5. Commit: `git commit -m "feat: describe your change"`
6. Push: `git push origin feature/your-feature`
7. Open a Pull Request

---

## Development Setup

### Backend
```bash
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

---

## Pull Request Process

- Keep PRs focused — one feature or fix per PR
- Update README.md if you add a new feature
- Make sure the app runs without errors before submitting
- Describe what you changed and why in the PR description

---

## Commit Convention

```
feat:     New feature
fix:      Bug fix
docs:     Documentation only
style:    CSS or formatting (no logic change)
refactor: Code restructuring
test:     Adding or updating tests
chore:    Build process or tooling
```

Example: `feat: add private messaging between users`

---

## Code Style

### Java (Backend)
- Use constructor injection — never `@Autowired` on fields
- Always use Lombok: `@Data`, `@Builder`, `@RequiredArgsConstructor`
- IP must always be read server-side from session attributes
- Never trust any client-supplied identity field

### JavaScript / React (Frontend)
- Functional components with hooks only — no class components
- Custom hooks go in `src/hooks/`
- One `export default` per file — never two
- CSS class names only — no large inline style objects

---

## Reporting Bugs

Open a GitHub Issue and include:
- What you expected to happen
- What actually happened
- Browser and OS version
- Console error messages (if any)
- Steps to reproduce

---

## Suggesting Features

Open a GitHub Issue with the label `enhancement` and describe:
- What problem it solves
- How you'd expect it to work
- Any implementation ideas you have
